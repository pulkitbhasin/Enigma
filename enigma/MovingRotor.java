package enigma;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Pulkit Bhasin
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    @Override
    public void setNotches(int change) {
        StringBuilder fun = new StringBuilder();
        for (int i = 0; i < _notches.length(); i++) {
            fun.append(alphabet().toChar(wrap
                    (alphabet().toInt(_notches.charAt(i)) - change)));
        }
        _notches = fun.toString();
    }



    @Override
    boolean rotates() {
        return true;
    }

    @Override
    void advance() {
        set(wrap(setting() + 1));
    }

    @Override
    boolean atNotch() {
        int i = 0;
        while (i < _notches.length()) {
            if (permutation().alphabet().toChar
                    (setting()) == _notches.charAt(i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    /** Return the value of P modulo the size of this permutation.
     * @param p integer defining p.
     * @param size integer defining size. */
    final int wrap(int p, int size) {
        int r = p % size;
        if (r < 0) {
            r += size;
        }
        return r;
    }

    /** Notches. */
    private String _notches;
}
