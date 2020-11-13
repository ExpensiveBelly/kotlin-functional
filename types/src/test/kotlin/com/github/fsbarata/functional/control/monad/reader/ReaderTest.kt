package com.github.fsbarata.functional.control.monad.reader

import com.github.fsbarata.functional.control.test.MonadLaws
import com.github.fsbarata.functional.data.Functor

private typealias Dependencies = Pair<String, Int>

class ReaderTest:
	MonadLaws<ReaderContext<Dependencies>> {
	override val monadScope = Reader.ReaderScope<Dependencies>()

	private val basicDependencies = Pair("ab", -38)

	override fun <A> Functor<ReaderContext<Dependencies>, A>.equalTo(other: Functor<ReaderContext<Dependencies>, A>) =
		asReader.run(basicDependencies) == other.asReader.run(basicDependencies)
}