package parser;

import Coffee.Command;
import Coffee.Products.Product;
import Coffee.Products.Taille;
import Coffee.State;
import Structures.Globals;
import server.ServerThread;

import javax.management.InstanceAlreadyExistsException;
import java.util.Locale;

public class admin_parser {
    private static Product add_product(ServerThread thread, String line) {
        Product new_product;
        String[] data = line.split(":")[1].split(",");

        try {
            new_product = new Product(
                    data[0],
                    data[1],
                    Integer.parseInt(data[2]),
                    Float.parseFloat(data[3]),
                    Float.parseFloat(data[4]),
                    Float.parseFloat(data[5]),
                    Float.parseFloat(data[6]),
                    Float.parseFloat(data[7])
            );
        } catch (IndexOutOfBoundsException e) {
            thread.stream.ecrireReseau("Erreur:Arguments manquants !");
            return null;
        } catch (NumberFormatException e) {
            thread.stream.ecrireReseau("Erreur:Nombre à virgule invalide !");
            return null;
        } catch (NullPointerException e) {
            thread.stream.ecrireReseau("Erreur:Nom, Type ou Durée invalide !");
            return null;
        }
        return new_product;
    }

    private static void modify_product(ServerThread thread, Product product, String line) {
        String[] args = line.split(":")[1].split(",");
        int status = 0;
        if (args.length != 4) {
            thread.stream.ecrireReseau("Erreur:Arguments manquants !");
            return;
        }
        switch (args[2]) {
            case "name":
                if (args[3].length() == 0) {
                    thread.stream.ecrireReseau("Erreur:Le nom ne peut pas être nul");
                    status = 1;
                } else {
                    product.setName(args[3]);
                }
                break;
            case "type":
                if (args[3].length() == 0) {
                    thread.stream.ecrireReseau("Erreur:Le type ne peut pas être nul");
                    status = 1;
                } else {
                    product.setType(args[3]);
                }
                break;
            case "temperature":
                try {
                    if (args[3].length() == 0) {
                        thread.stream.ecrireReseau("Erreur:La température ne peut pas être nulle");
                        status = 1;
                    } else {
                        float temp = Float.parseFloat(args[3]);
                        if (temp < 0) {
                            thread.stream.ecrireReseau("Erreur:La température ne peut pas être négative");
                            status = 1;
                        } else {
                            product.setTemperature(temp);
                        }
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur:Format de la température invalide");
                    status = 1;
                }
                break;
            case "prix":
                try {
                    if (args[3].length() == 0) {
                        thread.stream.ecrireReseau("Erreur:Le prix ne peut pas être nul");
                        status = 1;
                    } else {
                        float price = Float.parseFloat(args[3]);
                        if (price < 0) {
                            thread.stream.ecrireReseau("Erreur:Le prix ne peut pas être négatif");
                            status = 1;
                        } else {
                            product.setPrice(price);
                        }
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur:Format du prix invalide");
                    status = 1;
                }
                break;
            case "coffee_consumption":
                try {
                    if (args[3].length() == 0) {
                        thread.stream.ecrireReseau("Erreur:La valeur ne peut pas être nulle");
                        status = 1;
                    } else {
                        float coffee = Float.parseFloat(args[3]);
                        product.setCoffee_consumption(coffee);
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur:Format de la consommation invalide");
                    status = 1;
                }
                break;
            case "milk_consumption":
                try {
                    if (args[3].length() == 0) {
                        thread.stream.ecrireReseau("Erreur:La valeur ne peut pas être nulle");
                        status = 1;
                    } else {
                        float milk = Float.parseFloat(args[3]);
                        product.setMilk_consumption(milk);
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur:Format de la consommation invalide");
                    status = 1;
                }
                break;
            case "thea_consumption":
                try {
                    if (args[3].length() == 0) {
                        thread.stream.ecrireReseau("Erreur:La valeur ne peut pas être nulle");
                        status = 1;
                    } else {
                        float thea = Float.parseFloat(args[3]);
                        product.setThea_consumption(thea);
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur:Format de la consommation invalide");
                    status = 1;
                }
                break;
            default:
                thread.stream.ecrireReseau("Erreur: Attribut invalide !");
                status = 1;
        }
        if (status == 0)
            thread.stream.ecrireReseau("Modification effectuée avec succès !");
    }

    private static Taille add_taille(Globals lists, ServerThread thread, String line) {
        Taille new_taille;
        String[] data = line.split(":")[1].split(",");

        try {
            if (lists.search_taille_name(data[0]) != null)
                throw new InstanceAlreadyExistsException();
            new_taille = new Taille(
                    data[0],
                    Float.parseFloat(data[1]),
                    Float.parseFloat(data[2]),
                    Float.parseFloat(data[3]),
                    Float.parseFloat(data[4]));
        } catch (InstanceAlreadyExistsException e) {
            thread.stream.ecrireReseau("Erreur: Cette taille existe déjà !");
            return null;
        } catch (NumberFormatException e) {
            thread.stream.ecrireReseau("Erreur: Format du nombre de café, thé ou lait invalide !");
            return null;
        } catch (ArithmeticException e) {
            thread.stream.ecrireReseau("Erreur: Valeur de café, thé et lait ne peut pas être négatif !");
            return null;
        }
        return new_taille;
    }

    public static void parser_admin_cmds(Globals lists, ServerThread thread, String input) {
        String type = input.split(":")[0];
        switch (type) {
            case "ProductAdd":
                Product new_product = add_product(thread, input);
                if (new_product != null) {
                    lists.list_product.add(new_product);
                    thread.stream.ecrireReseau("Produit ajouté avec succès !");
                }
                break;
            case "ProductModify":
                String name = input.split(":")[1].split(",")[0];
                type = input.split(":")[1].split(",")[1];
                boolean found = false;
                for (Product product : lists.list_product) {
                    if (product.getName().equals(name) && product.getType().equals(type)) {
                        modify_product(thread, product, input);
                        found = true;
                    }
                }
                if (!found) {
                    thread.stream.ecrireReseau("Erreur:Produit non trouvé");
                }
                break;
            case "ProductRemove":
                name = input.split(":")[1].split(",")[0];
                type = input.split(":")[1].split(",")[1];
                found = false;
                int i = 0;
                for (Product product : lists.list_product) {
                    if (product.getName().equals(name) && product.getType().equals(type)) {
                        lists.list_product.remove(i);
                        found = true;
                        break;
                    }
                    ++i;
                }
                if (!found) {
                    thread.stream.ecrireReseau("Erreur:Produit non trouvé");
                } else {
                    thread.stream.ecrireReseau("Produit supprimé avec succès !");
                }
                break;
            case "TailleAdd":
                Taille new_taille = add_taille(lists, thread, input);
                if (new_taille != null) {
                    lists.list_taille.add(new_taille);
                    thread.stream.ecrireReseau("Taille ajoutée avec succès !");
                }
                break;
            case "TailleRemove":
                name = input.split(":")[1];
                found = false;
                for (Taille taille : lists.list_taille) {
                    if (taille.taille.equals(name)) {
                        lists.list_taille.remove(taille);
                        found = true;
                        break;
                    }
                }
                if (found)
                    thread.stream.ecrireReseau("Taille supprimée avec succès !");
                else
                    thread.stream.ecrireReseau("Error: Taille non trouvée !");
                break;
            case "CancelCmds":
                int nb = lists.coffee.CancelCmds(lists);
                thread.stream.ecrireReseau(nb + " commandes annulées");
                break;
            case "StockAdd":
                String quantity;
                try {
                    name = input.split(":")[1].split(",")[0];
                    quantity = input.split(":")[1].split(",")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    thread.stream.ecrireReseau("Erreur: Arguments manquants !");
                    name = null;
                    quantity = null;
                }
                try {
                    if (quantity != null && Float.parseFloat(quantity) < 0) {
                        thread.stream.ecrireReseau("Erreur: La quantité ne peut pas être négative.");
                        quantity = null;
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur: Format de quantité invalide.");
                }

                if (quantity != null && name != null) {
                    switch (name) {
                        case "coffee" -> {
                            lists.coffee.Remain_Coffee += Float.parseFloat(quantity);
                            if (lists.coffee.Remain_Coffee > lists.coffee.Capacity_Coffee)
                                lists.coffee.Remain_Coffee = lists.coffee.Capacity_Coffee;
                            thread.stream.ecrireReseau("Stock de café rechargé avec succès");
                        }
                        case "thea" -> {
                            lists.coffee.Remain_Thea += Float.parseFloat(quantity);
                            if (lists.coffee.Remain_Thea > lists.coffee.Capacity_Thea)
                                lists.coffee.Remain_Thea = lists.coffee.Capacity_Thea;
                            thread.stream.ecrireReseau("Stock de thé rechargé avec succès");
                        }
                        case "milk" -> {
                            lists.coffee.Remain_Milk += Float.parseFloat(quantity);
                            if (lists.coffee.Remain_Milk > lists.coffee.Capacity_Milk)
                                lists.coffee.Remain_Milk = lists.coffee.Capacity_Milk;
                            thread.stream.ecrireReseau("Stock de lait rechargé avec succès");
                        }
                        default -> thread.stream.ecrireReseau("Erreur:Nom de stock introuvable !");
                    }
                }
                break;
            case "CapacityMod":
                try {
                    name = input.split(":")[1].split(",")[0];
                    quantity = input.split(":")[1].split(",")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    thread.stream.ecrireReseau("Erreur: Arguments manquants !");
                    name = null;
                    quantity = null;
                }
                try {
                    if (quantity != null && Float.parseFloat(quantity) < 0) {
                        thread.stream.ecrireReseau("Erreur: La quantité ne peut pas être négative.");
                        quantity = null;
                    }
                } catch (NumberFormatException e) {
                    thread.stream.ecrireReseau("Erreur: Format de quantité invalide.");
                }

                if (quantity != null && name != null) {
                    switch (name) {
                        case "coffee" -> {
                            lists.coffee.Capacity_Coffee = Integer.parseInt(quantity);
                            thread.stream.ecrireReseau("Capacité de café modifié avec succès");
                            if (lists.coffee.Remain_Coffee > lists.coffee.Capacity_Coffee)
                                lists.coffee.Remain_Coffee = lists.coffee.Capacity_Coffee;
                        }
                        case "thea" -> {
                            lists.coffee.Capacity_Thea = Integer.parseInt(quantity);
                            thread.stream.ecrireReseau("Capacité de thé modifié avec succès");
                            if (lists.coffee.Remain_Thea > lists.coffee.Capacity_Thea)
                                lists.coffee.Remain_Thea = lists.coffee.Capacity_Thea;
                        }
                        case "milk" -> {
                            lists.coffee.Capacity_Milk = Integer.parseInt(quantity);
                            thread.stream.ecrireReseau("Capacité de lait avec succès");
                            if (lists.coffee.Remain_Milk > lists.coffee.Capacity_Milk)
                                lists.coffee.Remain_Milk = lists.coffee.Capacity_Milk;
                        }
                        default -> thread.stream.ecrireReseau("Erreur:Nom de stock introuvable !");
                    }
                }
                break;
            case "Poweroff":
                lists.coffee.state = State.OFFLINE;
                lists.coffee.CancelCmds(lists);
                while (lists.coffee.workingCmd()) {}
                lists.coffee.state = State.OFFLINE;
                break;
            default:
                thread.stream.ecrireReseau("Erreur:Commande introuvable !");
        }
    }
}
