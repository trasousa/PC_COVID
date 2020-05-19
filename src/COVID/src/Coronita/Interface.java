package COVID.src.Coronita;

import COVID.src.Exceptions.InvalidAcount;

public interface Interface {

        public void registarCliente();
        public void removerCliente() throws InvalidAcount;
        public void autenticar() throws InvalidAcount;
        public void atualizarEstimativa();
}

