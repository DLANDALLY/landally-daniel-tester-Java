package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;

public class FareDiscountCalculator extends Fare30MinutesCalculator{
    @Override
    public void calculateFare(Ticket ticket) {
        super.calculateFare(ticket);
    }

    public void calculateFare(Ticket ticket, boolean discount) {
        if (discount) {
            ticket.setPrice(applyDiscount(ticket.getPrice(), 5));
        }
        super.calculateFare(ticket);
    }

    public void todo(){
        //Quand un utilisateur entre dans le garage, il renseigne sa plaque
        // d’immatriculation.

        //Le système vérifie si cette plaque d’immatriculation a déjà été utilisée.

        //Si c’est le cas, le système renvoie le message suivant :
        // "Heureux de vous revoir ! En tant qu’utilisateur régulier de notre parking,
        // vous allez obtenir une remise de 5%", puis le système reprend son fonctionnement
        // habituel.

        //Quand l’utilisateur sort du parking, il bénéficie d’une réduction de 5% par
        // rapport au tarif normal.
    }
    public double applyDiscount(double originalPrice, double discountPercentage){
        return originalPrice * (1 - discountPercentage/100);
    }
    /**
     * Tout comme pour l’étape précédente, rédigez d’abord les tests vérifiant
     * qu’un véhicule muni d’un ticket de réduction paiera bien 95% du tarif plein.
     */

    /**
     * Écrivez un test unitaire le vérifiant dans le cas d’une voiture.Nom de la méthode
     * de test : calculateFareCarWithDiscountDescription du test :
     * ce test doit appeler la méthode
     * - calculateFare
     *     avec un ticket concernant une voiture et
     *     avec le paramètre discount à true,
     * puis vérifier que le prix calculé est est bien de 95% du
     * tarif plein. La durée du ticket doit être de plus de 30 minutes.
     */

    /**
     * Écrivez un test unitaire le vérifiant dans le cas d’une moto.Nom de la méthode de
     * test : calculateFareBikeWithDiscountDescription du test : ce test doit appeler la
     * méthode calculateFare avec un ticket concernant une moto et avec le paramètre
     * discount à true, puis vérifier que le prix calculé est est bien de 95% du tarif
     * plein. La durée du ticket doit être de plus de 30 minutes.
     */

    /**
     * Constatez que ces 2 tests échouent
     */
}
