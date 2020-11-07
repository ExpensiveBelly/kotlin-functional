package com.github.fsbarata.functional.rx.data

import com.github.fsbarata.functional.control.Functor
import com.github.fsbarata.functional.control.Monad
import com.github.fsbarata.functional.control.test.MonadTest
import com.github.fsbarata.functional.control.test.MonadZipTest

class ObservableFTest: MonadTest<ObservableF<*>>, MonadZipTest<ObservableF<*>> {
	override val monadScope = ObservableF
	override fun <A> Functor<ObservableF<*>, A>.equalTo(other: Functor<ObservableF<*>, A>): Boolean {
		val testObserver1 = asObservable.materialize().test()
		val testObserver2 = other.asObservable.materialize().test()

		return testObserver2.values() == testObserver1.values()
	}
}
