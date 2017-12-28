package my.learning

import java.util.function.IntPredicate

/**
 * Created by tdongsi on 12/23/17.
 */
class JavaNumberHelper {

    static int[] findPositives(int[] input) {
        return Arrays.stream(input).filter{it > 0}.toArray()
    }
}
