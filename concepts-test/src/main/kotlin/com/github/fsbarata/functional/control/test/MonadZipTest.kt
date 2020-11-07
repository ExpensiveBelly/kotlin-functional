package com.github.fsbarata.functional.control.test

import com.github.fsbarata.functional.control.*
import org.junit.Assert.assertTrue
import org.junit.Test

interface MonadZipTest<C>: MonadTest<C> {
	private val monad1 get() = monadScope.just(9) as MonadZip<C, Int>
	private val monad2 get() = monadScope.just(3) as MonadZip<C, Int>

	@Test
	fun naturality() {
		val f1 = { a: Int -> a * 2 }
		val f2 = { a: Int -> a + 2 }
		val f3 = { a: Int, b: Int -> a / b }
		val r1 =
			liftM<C>()(f1 split f2)
				.invoke(zip(monad1, monad2))
				.map { (a, b) -> f3(a, b) }
		val r2 =
			zip(monad1.map(f1) as MonadZip<C, Int>,
				monad2.map(f2) as MonadZip<C, Int>,
				f3)
		assertTrue("$r1 should be the same as $r2", r1.equalTo(r2))
	}

	@Test
	fun `information preservation`() {
		val (r1, r2) = unzip(zip(monad1, monad2))
		assertTrue(r1.equalTo(monad1))
		assertTrue(r2.equalTo(monad2))
	}
}