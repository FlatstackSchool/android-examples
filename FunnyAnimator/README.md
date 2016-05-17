##Пример использования Android animator
##### или property animator

(Официальный толковый подробный гайд)[http://developer.android.com/intl/ru/guide/topics/graphics/prop-animation.html]
(Либа с кучей нестандартных интересных анимаций)[https://github.com/daimajia/AndroidViewAnimations]

####Практика
Для анимации используются 3 класса: `ViewPropertyAnimator`, `ValueAnimator` и `ObjectAnimator`.

* **ViewPropertyAnimator** - Не является наследником от `ValueAnimator`.
(Официальные доки)[http://developer.android.com/intl/ru/reference/android/view/ViewPropertyAnimator.html]
(Блогпост из android-dev)[http://android-developers.blogspot.ru/2011/05/introducing-viewpropertyanimator.html]
на любой вьюшке можно вызвать метод `.animate()`, который вернет объект этого типа.
На нем можно вызвать кучу предустановленных анимаций. Например

`view.animate().setDuration(300).rotationBy(360).start();`

Обрати внимание, что если ты делаешь `setDuration(100)`, то все следующие анимации на этой вьюшке будут с такой же продолжительностью.
Также и с другими свойствами: `Listener`'ы, `Interpolator`'ы и проч.
Поэтому их надо сбрасывать при необходимости.
Также, если при выполнении одной анимации запустить другую, то первая просто остановится и начнется вторая.
Например, если есть анимация передвижения вьюшки по х от 0 до 100 и посередине начать вращение, то вьюшка начнет вращаться на координате 50 и после вращения так и останется там.
Учти это, если тебе нужно проигрывать до конца все анимации (см `AnimatorSet`)
Этот способ удобен для простых анимаций на 1-2-3 `View` и имеет самый понятный синтаксис.

* **ValueAnimator** [Официальная документация](https://developer.android.com/reference/android/animation/ValueAnimator.html)
Этот класс предоставляет наибольшую свободу действий. Отдаем в него время анимации, какие значение надо анимировать и интерполятор.
И добавляем свой колбек `ValueAnimator.AnimatorUpdateListener` в которых приходят промежуточные значения.
Их мы и используем по своему усмотрению.
Большой плюс здесь - можно задать один `ValueAnimator` и в колбеке обновлять сколько угодно `View`.

##### Inflate from .xml
[Документация](https://developer.android.com/guide/topics/graphics/prop-animation.html#declaring-xml)
С помощью `AnimatorInflator` можно создать из `.xml` файла `ObjectAnimator`, `ValueAnimator` и `AnimatorSet`.
См. пример в классе [AnimatorInflatorScreen](app/src/main/java/com/ilyaeremin/funnyanimator/AnimatorInflaterScreen.java)
Но в xml нельзя

#### Стандартые значения для animator
При создании `animator` интервал между кадрами - 10ms, время анимации 300ms, используется `LinearInterpolator`.

####Теория

Во всей абстракции android animator есть 3 участника: `Evaluator`, `Interpolator` и `Animator`
И входные данные: время анимации и интервал, который нужно анимировать, например два числа 0 и 360 (от 0 до 360), или 0.1 - 0.5 или цвет от Color.RED до Color.WHITE

**Evaluator** -  этот парень рассчитывают промежуточные значения (бывают для `int`, `float`, `argb`, и кастомные),
В Evaluator с каждым тиком анимации приходит значение `fraction` - текущее положение анимации от 0.00 до 1.00, где 0 начало, а 1.00 конец.
Также в него приходят значение начального и конечного положения анимации.
Evaluator с каждым тиком переводит проценты к нужному формату (`int`, `float`, `argb` и др).
Например, начальное положение 0, конечное 200, тогда входные выходные данные будут такими:

| fraction | start    | end   | output |
|:--------:|:--------:|:-----:|:------:|
|0     | 0   | 200 | 0   |
| 0.05 |  0  | 200 | 10   |
| 0.1  | 0   | 200 | 20  |
| ...  | ... | ... | ... |
| 0.95 | 0   | 200 | 190  |
| 1    | 0   | 200 | 200 |

**Interpolator** - парень, который вычисляет fraction для `Evaluator`.

Сравним `LinearInterpolator` и `AccelerateInterpolator`.
Для первого выходные значения будут такие: `0, 0.1, 0.2, 0.3, ... , 0.9, 1` - то есть анимация идет плавно, _линейно_.
Для второго примерно такие: 0, 0.05, 0.11, 0.17, 0.24, ..., 0.73, 0.95, 1 - шаг увеличивается с каждым кадром, создается эффект ускорения.

**Animator** - этот парень рассчитывает тайминги, когда делать каждый кадр, какие значения передавать в колбеки, содержит все `Listener`'ы, инфу нужно ли повторять анимацию.
Внутри себя использует `Evaluator` и `Interpolator`

Для того, чтобы все работало, необходимы два шага (помимо исходных данных: начальный, конечной точек и времени): вычислить значение для анимируемого параметра и применить это значение.
Два основных класса для работы с animator api: `ObjectAnimator` и `ValueAnimator` и первый наследуется от второго. `ValueAnimator` высчитывает время и значения для анимации, но то, как это значение использовать
решаешь ты. Например, ты хочешь анимировать значение от 0 до 100 за 1 секунду и в твой колбек через определенные промежутки времени будет приходить `int`, с которым ты сам решаешь что делать.
`ObjectAnimator` позволяет указать объект, который ты хочешь анимировать и имя метода без приставки set (то есть если метод setX, то в аниматор отдаем просто x) в который будут приходить значение.

```java
ObjectAnimator animator = new ObjectAnimator();
animator.setIntValues(0, 100);
animator.setTarget(yourAnimatedView);
animator.setInterpolator(new AccelerateDecelerateInterpolator());
animator.setDuration(1000);
animator.setPropertyName("x");
animator.start();
```

#### AnimatorSet
Анимации можно хитро комбинировать с помощью этого класса См пример [AnimatorSetScreen](app/src/main/java/com/ilyaeremin/funnyanimator/AnimatorSetScreen.java)

#### Разница с View Animations
[Официальное объяснение](https://developer.android.com/guide/topics/graphics/prop-animation.html#property-vs-view)
[Альтернативное объяснение на SO](http://stackoverflow.com/questions/28220613/what-is-the-difference-between-an-animator-and-an-animation)
От меня:
У Animators API level 14+, у Animations 1+.
Animator построены на Animations, в нем учтены проблемы при его использовании, улучшено API.
Поэтому если у тебя встает вопрос что изучать\использовать - выбор за animators как за более новым и удобным.
[Funny video animators vs animations](https://www.youtube.com/watch?v=VufDd-QL1c0)

#### TODO
KeyFrames
