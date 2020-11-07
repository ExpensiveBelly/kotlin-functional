package com.github.fsbarata.functional.data.either

import com.github.fsbarata.functional.control.Functor
import com.github.fsbarata.functional.control.test.MonadTest
import com.github.fsbarata.functional.data.maybe.Optional
import org.junit.Assert.*
import org.junit.Test

class EitherTest: MonadTest<Either<Nothing, *>> {
	override val monadScope = Either
	override fun <A> Functor<Either<Nothing, *>, A>.equalTo(other: Functor<Either<Nothing, *>, A>): Boolean =
		asEither == other.asEither

	@Test
	fun map() {
		assertEquals(LEFT, LEFT.map { it * 2 })
		assertEquals(Either.Right(10), RIGHT.map { it * 2 })
		assertEquals(Either.Right("5 a"), RIGHT.map { "$it a" })
	}

	@Test
	fun flatMap() {
		assertEquals(LEFT, LEFT.flatMap { Either.Right("$it a") })
		assertEquals(Either.Right("5 a"), RIGHT.flatMap { Either.Right("$it a") })
		assertEquals(LEFT, LEFT.flatMap { Either.Left("$it a") })
		assertEquals(Either.Left("5 a"), RIGHT.flatMap { Either.Left("$it a") })
	}

	@Test
	fun mapLeft() {
		assertEquals(Either.Left("5 a"), LEFT.mapLeft { "$it a" })
		assertEquals(RIGHT, RIGHT.mapLeft { "$it a" })
	}

	@Test
	fun fold() {
		assertEquals("5 a", LEFT.fold(ifLeft = { "$it a" }, ifRight = { "$it b" }))
		assertEquals("5 b", RIGHT.fold(ifLeft = { "$it a" }, ifRight = { "$it b" }))
		LEFT.fold(ifLeft = {}, ifRight = { fail() })
		RIGHT.fold(ifLeft = { fail() }, ifRight = {})
	}

	@Test
	fun orNull() {
		assertNull(LEFT.orNull())
		assertEquals(5, RIGHT.orNull())
	}

	@Test
	fun toOptional() {
		assertEquals(Optional.empty<Int>(), LEFT.toOptional())
		assertEquals(Optional.just(5), RIGHT.toOptional())
	}

	companion object {
		private val LEFT: Either<String, Int> = Either.Left("5")
		private val RIGHT: Either<String, Int> = Either.Right(5)
	}
}