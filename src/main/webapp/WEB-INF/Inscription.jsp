<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
</head>
<body>
	<h1>Inscription</h1>
	<form action="ServletInscription" method="post">
    <div>
        <label for="pseudo">Pseudo:</label>
        <input type="text" id="pseudo" name="pseudo">
    </div>
    <div>
        <label for="password">Mot De Passe:</label>
        <input type="password" id="password" name="motdepasse"></input>
    </div>
    <div>
        <label for="mail">e-mail:</label>
        <input type="email" id="mail" name="email">
    </div>
    <div>
        <label for="name">Nom:</label>
        <input type="text" id="name" name="Nom">
    </div>
    <div>
        <label for="prenom">Prénom:</label>
        <input type="text" id="prenom" name="Prenom">
    </div>
    <div>
        <label for="adresse">Rue:</label>
        <input type="text" id="adresse" name="Adresse">
    </div>
    <div>
        <label for="cp">Code Postal:</label>
        <input type="number" id="cp" name="Cp">
    </div>
    <div>
        <label for="ville">Ville:</label>
        <input type="text" id="ville" name="Ville">
    </div>
    <div>
        <label for="password">Confirmation:</label>
        <input type="password" id="password" name="Confirm"></input>
    </div>
     <div>
        <label for="telephone">Téléphone:</label>
        <input type="tel" id="telephone" name="Telephone" pattern="^(?:(?:\+|00)33[\s.-]{0,3}(?:\(0\)[\s.-]{0,3})?|0)[1-9](?:(?:[\s.-]?\d{2}){4}|\d{2}(?:[\s.-]?\d{3}){2})$">
       
    </div>
    
<input name="btCréer" type="submit" value="Créer"
title="Créer votre inscription">
<input name="btAnnuler" type="reset" value="Annuler"
title="Annulez votre saisie">
   <%@ include file="html/retourAccueil.html" %>
    
</form>
</body>
</html>
