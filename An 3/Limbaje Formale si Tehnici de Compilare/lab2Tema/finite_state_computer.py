class FiniteStateMachine:
    def __init__(self, alphabet, states, initial_state, transitions, final_states):
        self.alphabet = alphabet
        self.states = states
        self.initial_state = initial_state
        self.transitions = transitions
        self.final_states = final_states

    # alphabet = Lista de caractere (sau ce drq considera python ca is)
    # states  = lista de stringuri
    # initial_state = string (sau sir de caractere)
    # tranzitii = lista de stringuri
    # final_states = lista de stringuri

    def get_alphabet(self):
        return self.alphabet

    def get_transitions(self):
        return self.transitions

    def get_final_states(self):
        return self.final_states

    def get_states(self):
        return self.states

    def check_sequence(self, sequence):
        prefix = ""
        current_state = self.initial_state

        # Check for + or - sign at the beginning of the sequence
        if sequence and (sequence[0] == '+' or sequence[0] == '-'):
            sign = sequence[0]
            sequence = sequence[1:]
            found = False
            for transition in self.transitions:
                if (
                    transition.get_source_state() == current_state
                    # and transition.get_value() == sign
                ):
                    # prefix += sign
                    current_state = transition.get_destination_state()
                    found = True
                    break
            if not found:
                return False

        while sequence:
            found = False
            for transition in self.transitions:
                transition_value = transition.get_value()
                if (
                    transition.get_source_state() == current_state
                    and sequence.startswith(transition_value)
                ):
                    prefix += transition_value
                    sequence = sequence[len(transition_value):]
                    current_state = transition.get_destination_state()
                    found = True
                    break
            if not found:
                return False

        return current_state in self.final_states

    def get_longest_prefix(self, sequence):
        prefix = ""
        current_state = self.initial_state

        while sequence:
            found = False
            for transition in self.transitions:
                transition_value = transition.get_value()
                if (
                    transition.get_source_state() == current_state
                    and sequence.startswith(transition_value)
                ):
                    prefix += transition_value
                    sequence = sequence[len(transition_value):]
                    current_state = transition.get_destination_state()
                    found = True
                    break
            if not found:
                return prefix

        return prefix