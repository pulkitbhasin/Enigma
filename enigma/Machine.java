package enigma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Pulkit Bhasin
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        if (numRotors <= 1) {
            throw new EnigmaException("Insufficient rotors");
        } else if (pawls < 0) {
            throw new EnigmaException("Insufficient pawls");
        } else {
            _alphabet = alpha;
            _numRotors = numRotors;
            _pawls = pawls;
            _allRotors = allRotors;
            _rotorSlots = new ArrayList<>();
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        _rotorSlots = new ArrayList<>();
        int counter = 0;
        for (int a = 0; a < rotors.length; a++) {
            if (!rotors[a].equals("")) {
                counter++;
            }
        }
        if (counter != numRotors()) {
            throw new EnigmaException("Incorrect number of rotor slots");
        } else {
            boolean movingRotorFound = false;
            int movingRotorsCounter = 0;
            for (int i = 0; i < numRotors(); i++) {
                boolean flag = false;
                for (Rotor rotor : _allRotors) {
                    if (rotor.name().equals(rotors[i])) {
                        if (i == 0 && !rotor.reflecting()) {
                            throw new EnigmaException("First rotor "
                                    + "must be a reflector");
                        } else if (i != 0 && rotor.reflecting()) {
                            throw new EnigmaException("Reflector "
                                    + "can only be the first rotor");
                        } else if (movingRotorFound && !rotor.rotates()) {
                            throw new EnigmaException("Must be a moving rotor");
                        } else {
                            if (rotor.rotates()) {
                                movingRotorFound = true;
                                movingRotorsCounter++;
                            }
                            for (Rotor lol : _rotorSlots) {
                                if (lol.name().equals(rotor.name())) {
                                    throw new EnigmaException("Rotor repeated");
                                }
                            }
                            _rotorSlots.add(rotor);
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    throw new EnigmaException("Rotor unavailable");
                }
            }
            if (movingRotorsCounter != numPawls()) {
                throw new EnigmaException("Incorrect number of moving rotors");
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != (numRotors() - 1)) {
            throw new EnigmaException("Incorrect length");
        } else {
            Iterator iterRotors = _rotorSlots.iterator();
            iterRotors.next();
            while (iterRotors.hasNext()) {
                Rotor rotor = (Rotor) iterRotors.next();
                rotor.set(setting.charAt(0));
                setting = setting.substring(1);
            }
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        if (c < 0 || c >= _alphabet.size()) {
            throw new EnigmaException("Index not in range");
        } else {
            ArrayList<Rotor> advancedRotors = new ArrayList<>();
            for (int i = 0; i < _rotorSlots.size() - 1; i++) {
                if (_rotorSlots.get(i + 1).atNotch()
                        && _rotorSlots.get(i).rotates()
                        && _rotorSlots.get(i + 1).rotates()) {
                    if (!advancedRotors.contains(_rotorSlots.get(i))) {
                        _rotorSlots.get(i).advance();
                        advancedRotors.add(_rotorSlots.get(i));
                    }
                    if ((i + 1) != _rotorSlots.size() - 1) {
                        if (!advancedRotors.contains(_rotorSlots.get(i + 1))) {
                            _rotorSlots.get(i + 1).advance();
                            advancedRotors.add(_rotorSlots.get(i + 1));
                        }
                    }
                }
            }
            _rotorSlots.get(_rotorSlots.size() - 1).advance();
            int returnChar = _plugboard.permute(c);
            for (int j = _rotorSlots.size() - 1; j >= 0; j -= 1) {
                returnChar = _rotorSlots.get(j).convertForward(returnChar);
            }
            for (int k = 1; k < _rotorSlots.size(); k++) {
                returnChar = _rotorSlots.get(k).convertBackward(returnChar);
            }
            return _plugboard.invert(returnChar);
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        StringBuilder finalMessage = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) != ' ') {
                finalMessage.append(_alphabet.
                        toChar(convert(_alphabet.toInt(msg.charAt(i)))));
            }
        }
        return finalMessage.toString();
    }

    /** Returns rotorSlots.  */
    public ArrayList<Rotor> getRotors() {
        return _rotorSlots;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /** Number of rotors. */
    private int _numRotors;
    /** Number of pawls.  */
    private int _pawls;
    /** All available rotors. */
    private Collection<Rotor> _allRotors;
    /** Rotors in current machine. */
    private ArrayList<Rotor> _rotorSlots;
    /** Plugboard. */
    private Permutation _plugboard;
}
