package com.emsi.soap_service.service;


import com.emsi.soap_service.entity.Compte;
import com.emsi.soap_service.entity.TypeCompte;
import com.emsi.soap_service.repo.CompteRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@WebService(name = "CompteSoapService")
@AllArgsConstructor
public class CompteSoapService {
    private CompteRepository compteRepository;

    @WebMethod
    public List<Compte> getComptes() {
        return compteRepository.findAll();
    }

    @WebMethod
    public Compte getCompteById(@WebParam(name = "id") Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    @WebMethod
    @WebResult(name = "compte")
    public Compte createCompte(@WebParam(name = "solde") double solde,
                               @WebParam(name = "type") TypeCompte type) throws NegativeBalanceException {
        if (solde < 0) {
            throw new NegativeBalanceException("Le solde ne peut pas etre negatif");
        }

        Compte compte = new Compte(solde, type);
        return compteRepository.save(compte);
    }

    @WebMethod
    public boolean deleteCompte(@WebParam(name = "id") Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}