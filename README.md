# FoodTruck-App
Take home project

For this app I've made several decisions. One decision was to use ViewBinding over alternatives such as findViewById, Kotlin Synthetics, and ButterKnife. I chose ViewBinding because of its null safety; with Kotlin Synthetics for example, there is the possibility of accessing null views, however, with ViewBinding the binding class is generated in a single pass. The main decision in the app was to use MVVM (Model–view–viewmodel). I chose this architecture because of the clear seperation of concerns that it enforces between the presentation and the data layer; and this is supported with Android Jetpack classes like LiveData. Classes like ViewModel don't require the programmer to manually release clear some Presentation object like with the Model-View-Presenter architecture, as the ViewModel receives information about the current lifecycle the fragment/activity is in which allows for maintaining state across lifecycle changes. I used RxJava for the networking because it's a battle tested networking library and I have used it more extensively than Kotlin Coroutines; I like both of these options but I do find the functional operators and error handling of RxJava are really nice. I've also used Navigation Components in this app because it abstracts a lot of the Fragment API out of the app and makes moving between destinations easier. Safe Args is also quite nice because of the type safety as opposed to bundles. 
