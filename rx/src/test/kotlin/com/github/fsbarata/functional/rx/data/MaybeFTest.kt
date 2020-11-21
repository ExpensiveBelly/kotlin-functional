package com.github.fsbarata.functional.rx.data

import com.github.fsbarata.functional.control.MonadZipLaws
import com.github.fsbarata.functional.data.Functor
import com.github.fsbarata.functional.rx.maybeFactory
import java.io.IOException

class MaybeFTest: MonadZipLaws<MaybeF<*>> {
	override val monadScope = MaybeF
	override fun <A> Functor<MaybeF<*>, A>.equalTo(other: Functor<MaybeF<*>, A>): Boolean {
		val testObserver1 = asMaybe.materialize().test()
		val testObserver2 = other.asMaybe.materialize().test()

		return testObserver2.values() == testObserver1.values()
	}

	val error = IOException()
	override val possibilities: Int = 4
	override fun factory(possibility: Int) = maybeFactory(possibility)
}
