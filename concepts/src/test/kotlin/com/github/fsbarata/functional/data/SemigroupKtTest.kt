package com.github.fsbarata.functional.data

import org.junit.Assert.assertEquals
import org.junit.Test

class SemigroupKtTest {
	data class SgInt(val i: Int): Semigroup<SgInt> {
		override fun combineWith(other: SgInt) = SgInt(i + other.i)
	}

	data class SgString(val str: String): Semigroup<SgString> {
		override fun combineWith(other: SgString) = SgString(str + other.str)
	}

	@Test
	fun stimes() {
		assertEquals(SgInt(3), SgInt(3).stimes(1))
		assertEquals(SgInt(45), SgInt(3).stimes(15))
		assertEquals(SgString("4a4a4a4a"), SgString("4a").stimes(4))
	}

	@Test
	fun dual() {
		assertEquals(SgString("5a3w"), SgString("3w").dual().combineWith(SgString("5a").dual()).get)
	}
}
