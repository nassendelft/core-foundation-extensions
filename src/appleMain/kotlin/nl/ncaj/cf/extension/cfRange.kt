package nl.ncaj.cf.extension

import kotlinx.cinterop.convert
import platform.CoreFoundation.CFRangeMake

fun IntRange.toCFRange() = CFRangeMake((first).convert(), (last - first).convert())
