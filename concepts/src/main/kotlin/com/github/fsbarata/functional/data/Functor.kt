package com.github.fsbarata.functional.data

import com.github.fsbarata.functional.control.Context

interface Functor<F, out A>: Context<F, A> {
	fun <B> map(f: (A) -> B): Functor<F, B>
}
