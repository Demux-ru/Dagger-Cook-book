## Пошаговая инструкция как использовать Dagger 2
##### В данной инструкции рассмотрен пример реализации DI  при помощи Dagger-Android.

#### Добавляем библиотеку в проект

Подключаем необходимые зависимости в наш проект:

```
dependencies {
    implementation 'com.google.dagger:dagger:2.27'
    kapt 'com.google.dagger:dagger-compiler:2.27'

    implementation 'com.google.dagger:dagger-android-support:2.27'
    kapt 'com.google.dagger:dagger-android-processor:2.27'
}
```

#### Создаем модули

##### Data-слой

В модулях указывается что именно мы будем провайдить в качестве зависимостей.
Например, для работы с сетью нам нужно создать `Retrofit` клиента и уже на data слое нашего приложения (например в нашем `DataSource`)
вызывать нужный метод у клиента. Для это создаем модуль `ApiModule`, в котором будем провайдить `Retrofit` клиента.

```
@Module
class ApiModule {

    @Provides
    @AppScope
    fun provideCurrenciesApiClient(retrofit: Retrofit): CurrenciesApiClient =
        retrofit.create(CurrenciesApiClient::class.java)

    @Provides
    @AppScope
    fun provideRetrofit(
        okHttpClient: OkHttpClient?,
        gsonConverterFactory: GsonConverterFactory?
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @AppScope
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @AppScope
    fun provideGson(): Gson = Gson()
}
```

Далее  создаем наш модуль data-слоя, состоящего из датасорса и репозитория:

```
@Module(includes = [ApiModule::class])
interface DataModule {

    @Binds
    @AppScope
    fun bindCurrenciesDataSource(dataSource: CurrenciesDataSourceImpl): CurrenciesDataSource


    @Binds
    @AppScope
    fun bindCurrenciesRepository(repository: CurrenciesRepositoryImpl): CurrenciesRepository
}
```

Обращаю внимание, если вы используете аннотацию `@Binds`, то зависимости необходимо инжектирить в конструктор класса при помощи аннотации `@Inject`.
А если в `DataModule` модуле была бы указана аннотация `@Provides`, то в самом классе `DataSource` аннотацию `@Inject` нет надобности указывать.
Это обуславливается тем, что используя аннотацию `@Provides` вы самостоятельно, руками в модуле предоставляете необходимые зависимости.

Надеюсь вы были внимательны и обратили внимание на то, что во всех модулях выше указана аннотация `@AppScope`, которая означает - объекты созданные в данных модулях
будут жить на всем протяжении жизни приложения, и не будут пересоздаваться.

##### UI-слой

У объектов и UI слоя время жизни отличается от времени жизни приложения (речь идет об активити, фрагментах), поэтому у них есть свой скоуп
и они помечаются соответствующими аннотациями. Для начала давайте создадим отдельный модуль для фрагмента. Допустим, что фрагменту для работы
вьюмодель, поэтому давайте запродадим её зависимость в модуле:

```
@Module
interface CurrenciesFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrenciesViewModel::class)
    fun bindCurrenciesViewModel(viewModel: CurrenciesViewModel): ViewModel
}
```
Далее создадим модуль для активити, в котором укажем, что ранее созданный фрагмент будет использоваться внутри активити.

```
@Module
interface StartActivityModule {

    @Binds
    @ActivityScope
    fun bindStartActivity(activity: StartActivity): Activity

    @FragmentScope
    @ContributesAndroidInjector(modules = [CurrenciesFragmentModule::class])
    fun provideCurrenciesFragment(): CurrenciesFragment
}
```

Как вы можете заметить: для фрагмента добавлена соответствующая аннотация `@FragmentScope`, а для активити -  аннотация `@ActivityScope`.
Данными аннотациями определяется время жизни объектов, над которыми они были поставлены. Т.е. если была использована
аннотация `@FragmentScope` - объект будет жить, пока живет фрагмент в который данные зависимости провайдятся. Как только
фрагмент уничтожится, то объекты указанные в модуле тоже перестанут существовать.  
Аналогичная ситуация и для аннотации `@ActivityScope`, только время жизни объектов = времени жизни актвити.

Затем создаем главный `AppModule`, который будет провайдить нашу активитити с нужным скопом в `Application`.

```
@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [StartActivityModule::class])
    fun injectStartActivity(): StartActivity
}
```

`AndroidSupportInjectionModule` - это встроенный модуль dagger-android, который согласно документации, должен быть обязательно добавлен
в root компонент, для обеспечения доступности всех `AndroidInjector`.

`@ContributesAndroidInjector` - это аннотация, которая сгенерирует `AndroidInjector` для возвращаемого типа, таким образом даггер сможет
инжектить зависимости в данную активити. Так же будет сгенерирован сабкомпонент для `StartActivity` со скоупом `@ActvitiyScope`. 
`AndroidInjection` — это по сути сабкомпонент для конкретной активити, при этом мы можем указать какие модули будут относится только
к этому конкретному активити. Данный `AndroidInjector` будет иметь все зависимости этого модуля(`AppModule`) плюс зависимости,
которые указаны в модулях аннотации `ContributesAndroidInjector`.

#### Создаем компонент
Для построения графа зависимостей, для каждого модуля мы должны указать компонент, где он будет применяться, т.е. куда данные
зависимости нужно заинжектить, кому отдать. В нашем случае мы будет все модули аккумулироваться в одном компоненте, `AppComponent`.
Но при использовании Dagger-Android мы будем инжектить нужные зависимости сразу в базовые компоненты андройда.

```
@Component(modules = [
        AppModule::class,
        DataModule::class
    ])
@AppScope
interface AppComponent : AndroidInjector<DiTestApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DiTestApp>() {

        @BindsInstance
        abstract fun app(app: Application): Builder

        @BindsInstance
        abstract fun context(context: Context): Builder
    }
}
```
#### Инжектим главный компонент в Application

В последнюю очередь нам необходимо заинжектить AppComponent непосредственно в наш класс Application.

```
class DiTestApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
            super.onCreate()

            DaggerAppComponent.builder()
                .app(this)
                .context(this)
                .create(this)
                .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
```
`DispatchingAndroidInjector` нужен для поиска `AndroidInector` конкретного `Activity`.

#### Используем нужные зависимости в активити

```
class StartActivity : AppCompatActivity(), HasAndroidInjector {

	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Any>

	override fun onCreate(savedInstanceState: Bundle?) {
	        AndroidInjection.inject(this)
	        super.onCreate(savedInstanceState)
	        setContentView(R.layout.activity_start)
	}

	override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
```

При вызове `AndroidInjection.inject(this)`, Dagger 2 получается доступ к `Application`,
который реализует интерфейс `HasActivityInjector`, где через диспетчер находит нужный `AndroidInector` (сабкомпонент активити)
по классу активити и затем производит инициализацию зависимостей с нужным скоупом.

#### Используем нужные зависимости во фрагменте

```
lass CurrenciesFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CurrenciesViewModel = injectViewModel(viewModelFactory)

    override fun onAttach(context: Context) {
            AndroidSupportInjection.inject(this)
            super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.Currencies_fragment, container, false)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
```

### Done! Поздравляю, вы реализовали правильный DI в вашем приложении.