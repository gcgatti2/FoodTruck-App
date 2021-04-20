# FoodTruck-App
Take home project

For this app I've made several decisions. One decision was to use ViewBinding over alternatives such as findViewById, Kotlin Synthetics, and ButterKnife. I chose ViewBinding because of its null safety; with Kotlin Synthetics for example, there is the possibility of accessing null views, however, with ViewBinding the binding class is generated in a single pass.
