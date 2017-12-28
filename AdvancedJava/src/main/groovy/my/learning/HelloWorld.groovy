package my.learning

import java.util.stream.Collectors

/**
 * Created by tdongsi on 12/5/17.
 */

println "Hello World"

// https://www.youtube.com/watch?v=BXRDTiJfrSE
// Power of twos
def first = (0..<12).collect { 2 ** it}
println first

// Mix of Groovy and Java 8
def second = (0..<12).stream().map{2**it}.collect(Collectors.toList())
println second

