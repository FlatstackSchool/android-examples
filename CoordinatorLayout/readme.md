Этот [Layout](http://developer.android.com/intl/ru/reference/android/support/design/widget/CoordinatorLayout.html) совместно с [CoordinatorLayout.Behavior](http://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.Behavior.html) мопогают реализовывать интерфейсы, в которых расположение/размер объектов зависит от расположения/размера других объектов. Например:

![](https://github.com/fs/android-examples/blob/master/CoordinatorLayout/assert/coordinator.gif?raw=true)


В этом репозитории - одна из возможных реализаций интерфейса представленного на гифке выше.

Важной единицей этого контейнера является - [CoordinatorLayout.Behavior](http://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.Behavior.html). С его помощью потомки контейнера могут реагировать на изменение положения своих соседей. В этом примере круглый логотип отслеживает положение Toolbar`а и соответственным образом изменяет свое положение и размеры.

Подробнее:
* http://developer.android.com/intl/ru/reference/android/support/design/widget/CoordinatorLayout.html

* https://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout
