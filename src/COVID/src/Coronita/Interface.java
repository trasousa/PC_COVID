package COVID.src.Coronita;

import COVID.src.Exceptions.InvalidAcount;

public interface Interface {
        public void registarCliente(String id, String passwd);
        public void removerCliente(String id) throws InvalidAcount;
        public void autenticar(String id,String passwd) throws InvalidAcount;
        public void atualizarEstimativa(int cases);
}

