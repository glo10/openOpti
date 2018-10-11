-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 24 août 2018 à 02:17
-- Version du serveur :  5.7.21
-- Version de PHP :  5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `openopti`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `nom_client` varchar(80) NOT NULL,
  `prenom_client` varchar(80) NOT NULL,
  `date_naissance` date NOT NULL,
  `num_secu` varchar(15) NOT NULL,
  `adresse_client` varchar(150) DEFAULT NULL,
  `cp_client` varchar(5) DEFAULT NULL,
  `ville_client` varchar(150) DEFAULT NULL,
  `email_client` varchar(80) DEFAULT NULL,
  `tel_client` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `nom_client` (`nom_client`,`prenom_client`,`date_naissance`,`num_secu`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id_com` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(80) NOT NULL,
  `com` varchar(1500) NOT NULL,
  `date_com` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `opticien` varchar(80) DEFAULT NULL,
  `facture` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id_com`),
  KEY `fk_commentaire_facture` (`facture`),
  KEY `fk_commentaire_opticien` (`opticien`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE IF NOT EXISTS `facture` (
  `num_facture` varchar(80) NOT NULL,
  `date_facture` date NOT NULL,
  `libelle_equipement` varchar(9) NOT NULL,
  `date_creation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_envoi` date NOT NULL,
  `montant_total` decimal(5,2) NOT NULL,
  `date_paiement_mut` date DEFAULT NULL,
  `montant_remb_mut` decimal(5,2) NOT NULL,
  `date_paiement_secu` date DEFAULT NULL,
  `montant_remb_secu` decimal(5,2) DEFAULT NULL,
  `num_lot` int(11) DEFAULT NULL,
  `mutuelle` int(11) DEFAULT NULL,
  `secu` int(11) DEFAULT NULL,
  `client` int(11) NOT NULL,
  `etat` varchar(25) NOT NULL DEFAULT 'envoyée',
  PRIMARY KEY (`num_facture`),
  KEY `fk_facture_secu` (`secu`),
  KEY `fk_facture_client` (`client`),
  KEY `fk_facture_mutuelle` (`mutuelle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Structure de la table `mutuelle`
--

DROP TABLE IF EXISTS `mutuelle`;
CREATE TABLE IF NOT EXISTS `mutuelle` (
  `id_mutuelle` int(11) NOT NULL AUTO_INCREMENT,
  `nom_mutuelle` varchar(80) NOT NULL,
  `adresse_mutuelle` varchar(80) NOT NULL,
  `ville_mutuelle` varchar(80) NOT NULL,
  `cp_mutuelle` varchar(5) NOT NULL,
  `tel_mutuelle` varchar(10) DEFAULT NULL,
  `email_mutuelle` varchar(80) DEFAULT NULL,
  `fax_mutuelle` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_mutuelle`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;



--
-- Structure de la table `opticien`
--

DROP TABLE IF EXISTS `opticien`;
CREATE TABLE IF NOT EXISTS `opticien` (
  `login` varchar(80) NOT NULL,
  `nom_opticien` varchar(80) NOT NULL,
  `prenom_opticien` varchar(80) NOT NULL,
  `fonction_opticien` varchar(80) NOT NULL,
  `mdp` varchar(80) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Structure de la table `secu`
--

DROP TABLE IF EXISTS `secu`;
CREATE TABLE IF NOT EXISTS `secu` (
  `id_secu` int(11) NOT NULL AUTO_INCREMENT,
  `nom_secu` varchar(80) NOT NULL,
  `adresse_secu` varchar(80) NOT NULL,
  `ville_secu` varchar(80) NOT NULL,
  `cp_secu` varchar(5) NOT NULL,
  `tel_secu` varchar(10) DEFAULT NULL,
  `email_secu` varchar(80) DEFAULT NULL,
  `fax_secu` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_secu`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Structure de la table `souscrit_contrat`
--

DROP TABLE IF EXISTS `souscrit_contrat`;
CREATE TABLE IF NOT EXISTS `souscrit_contrat` (
  `id_mutuelle` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `date_deb` date NOT NULL,
  `date_fin` date NOT NULL,
  `num_contrat` varchar(80) DEFAULT NULL,
  `num_adherent` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id_mutuelle`,`id_client`,`date_deb`),
  KEY `fk_souscrit_client` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `fk_commentaire_facture` FOREIGN KEY (`facture`) REFERENCES `facture` (`num_facture`),
  ADD CONSTRAINT `fk_commentaire_opticien` FOREIGN KEY (`opticien`) REFERENCES `opticien` (`login`) ON DELETE SET NULL;

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `fk_facture_client` FOREIGN KEY (`client`) REFERENCES `client` (`id_client`),
  ADD CONSTRAINT `fk_facture_mutuelle` FOREIGN KEY (`mutuelle`) REFERENCES `mutuelle` (`id_mutuelle`) ON DELETE SET NULL,
  ADD CONSTRAINT `fk_facture_secu` FOREIGN KEY (`secu`) REFERENCES `secu` (`id_secu`) ON DELETE SET NULL;

--
-- Contraintes pour la table `souscrit_contrat`
--
ALTER TABLE `souscrit_contrat`
  ADD CONSTRAINT `fk_souscrit_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_souscrit_mutuelle` FOREIGN KEY (`id_mutuelle`) REFERENCES `mutuelle` (`id_mutuelle`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*modification taille du champs libelle_equipement*/
ALTER TABLE `facture` CHANGE `libelle_equipement` `libelle_equipement` VARCHAR(13) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL;

--
-- Déclencheurs `facture`
--
DROP TRIGGER IF EXISTS `trigger_etat`;
DELIMITER $$
CREATE TRIGGER `trigger_etat` BEFORE UPDATE ON `facture` FOR EACH ROW IF (OLD.etat = "remb secu ok")
	AND (NEW.etat = "remb mut ok")
	AND (OLD.libelle_equipement <> "lentilles NR")
		THEN
		SET NEW.etat = "acquittée";

ELSEIF (OLD.etat = "remb mut ok")
AND (NEW.etat = "remb secu ok")
	THEN
	SET NEW.etat = "acquittée";
ELSEIF (NEW.etat = "remb mut ok")
AND (OLD.libelle_equipement = "lentilles NR")
	THEN
	SET NEW.etat = "acquittée";
END IF
$$
DELIMITER ;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
