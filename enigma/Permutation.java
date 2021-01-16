package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Pulkit Bhasin
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        _cycles = _cycles + " " + cycle;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return alphabet().size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        String cycles = _cycles;
        int j = 0;
        StringBuilder cycle = new StringBuilder();
        boolean flag = false;
        while (j < cycles.length()) {
            if (cycles.charAt(j) == ')' && flag) {
                break;
            } else if (cycles.charAt(j) != '(' && cycles.charAt(j) != ' ') {
                if (cycles.charAt(j) == _alphabet.toChar(wrap(p))) {
                    flag = true;
                }
                cycle.append(cycles.charAt(j));
            } else {
                cycle = new StringBuilder();
            }
            j++;
        }
        j = 0;
        if (!flag) {
            return p;
        }
        flag = false;
        while (!flag) {
            if (cycle.charAt(j) == _alphabet.toChar(wrap(p))) {
                flag = true;
            }
            j++;
        }
        if (j == cycle.length()) {
            j = 0;
        }
        int k = 0;
        while (k < _alphabet.size()) {
            if (_alphabet.toChar(k) == cycle.charAt(j)) {
                break;
            }
            k++;
        }
        return k;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        String cycles = _cycles;
        int j = 0;
        StringBuilder cycle = new StringBuilder();
        boolean flag = false;
        while (j < cycles.length()) {
            if (cycles.charAt(j) == ')' && flag) {
                break;
            } else if (cycles.charAt(j) != '(' && cycles.charAt(j) != ' ') {
                if (cycles.charAt(j) == _alphabet.toChar(wrap(c))) {
                    flag = true;
                }
                cycle.append(cycles.charAt(j));
            } else {
                cycle = new StringBuilder();
            }
            j++;
        }
        j = 0;
        if (!flag) {
            return c;
        }
        flag = false;
        while (!flag) {
            if (cycle.charAt(j) == _alphabet.toChar(wrap(c))) {
                flag = true;
            }
            j++;
        }
        j = j - 2;
        if (j == -1) {
            j = cycle.length() - 1;
        }
        int k = 0;
        while (k < _alphabet.size()) {
            if (_alphabet.toChar(k) == cycle.charAt(j)) {
                break;
            }
            k++;
        }
        return k;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        if (_alphabet.contains(p)) {
            return _alphabet.toChar(permute(_alphabet.toInt(p)));
        } else {
            throw new EnigmaException("Character not in alphabet");
        }
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (_alphabet.contains(c)) {
            return _alphabet.toChar(invert(_alphabet.toInt(c)));
        } else {
            throw new EnigmaException("Character not in alphabet");
        }
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int i = 0;
        while (i < _alphabet.size()) {
            if (_alphabet.toChar(i) == permute(_alphabet.toChar(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    /** Cycles of this permutation.  */
    private String _cycles;
}
