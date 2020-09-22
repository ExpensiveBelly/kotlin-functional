package com.fsbarata.fp.concepts

interface Foldable<out T> {
	fun <R> fold(initialValue: R, accumulator: (R, T) -> R): R
}

fun <T> Foldable<T>.toList() = fold(emptyList<T>()) { list, item -> list + item }
