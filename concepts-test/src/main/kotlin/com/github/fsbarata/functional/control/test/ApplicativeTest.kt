package com.github.fsbarata.functional.control.test

import com.github.fsbarata.functional.control.Applicative
import com.github.fsbarata.functional.control.apFromLift
import com.github.fsbarata.functional.control.liftA2FromAp
import com.github.fsbarata.functional.data.F1
import com.github.fsbarata.functional.data.compose
import com.github.fsbarata.functional.data.curry
import com.github.fsbarata.functional.data.id
import org.junit.Test
import kotlin.math.roundToInt

interface ApplicativeTest<C>: FunctorTest<C> {
	val applicativeScope: Applicative.Scope<C>
	override fun <A> createFunctor(a: A): Applicative<C, A> {
		return applicativeScope.just(a)
	}

	@Test
	fun `just identity`() {
		val v = applicativeScope.just(5)
		val v2 = v.ap(applicativeScope.just(id()))
		assert(v.equalTo(v2))
	}

	@Test
	fun `ap composition`() {
		val u = applicativeScope.just { a: Int -> a * 2 }
		val v = applicativeScope.just { a: Int -> a + 2 }
		val w = applicativeScope.just(5)
		val r1 = w.ap(v).ap(u)
		val comp =
			applicativeScope.just { f1: F1<Int, Int> -> { f2: F1<Int, Int> -> f1.compose(f2) } }
		val r2 = w.ap(v.ap(u.ap(comp)))
		assert(r1.equalTo(r2)) { "$r1 must be equal to $r2" }
	}

	@Test
	fun `ap homomorphism`() {
		assert(
			applicativeScope.just(10)
				.equalTo(
					applicativeScope.just(5)
						.ap(applicativeScope.just { it * 2 })
				)
		)
	}

	@Test
	fun `ap interchange`() {
		val u = applicativeScope.just { a: Int -> a * 2 }
		val r1 = applicativeScope.just(5).ap(u)
		val r2 = u.ap(applicativeScope.just { it(5) })
		assert(r1.equalTo(r2)) { "$r1 must be equal to $r2" }
	}

	@Test
	fun `liftA2 = liftA2FromAp`() {
		val u = applicativeScope.just(5)
		val v = applicativeScope.just(1.3)
		val f = { a: Int, b: Double -> (a * b).toString() }.curry()
		val r1 = liftA2FromAp(u, v, f)
		val r2 = u.liftA2(f)(v)
		assert(r1.equalTo(r2)) { "$r1 must be equal to $r2" }
	}

	@Test
	fun `ap = apFromLift`() {
		val u = applicativeScope.just(5)
		val f = applicativeScope.just { a: Int -> (a * 0.5).roundToInt() }
		val r1 = apFromLift(u, f)
		val r2 = u.ap(f)
		assert(r1.equalTo(r2)) { "$r1 must be equal to $r2" }
	}
}