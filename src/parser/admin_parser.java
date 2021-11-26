package parser;

import Coffee.Products.Product;
import Structures.Globals;
import server.ServerThread;

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
                for (Product product: lists.list_product) {
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
                for (Product product: lists.list_product) {
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
            default:
                thread.stream.ecrireReseau("Erreur:Commande introuvable !");
        }
    }
}
