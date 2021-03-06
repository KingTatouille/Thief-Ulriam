package fr.hillwalk.thief.commands;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Items;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.configs.PlayersConfig;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commandes implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //On possède la classe util ici
        UtilsRef util = new UtilsRef();
        PlayersConfig config = new PlayersConfig();

        List<String> voleurs = new ArrayList<String>();

        //Si le sender est un joueur
        if(sender instanceof Player){



            //On possède le joueur ici
            Player player = (Player) sender;

            //Si le joueur n'est pas op ou ne possède pas la permission alors il ne peut pas faire les commandes.
            if(!(player.isOp() || player.hasPermission("thief.steal"))){

                player.sendMessage(Thief.prefix + "Vous n'avez pas les droits requis pour faire cette commande !");
                return true;

            }

            //Si le joueur fait /steal sans arguments
            if(args.length == 0){

                player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("commandes.syntax")));
                return true;

            }

            if (args[0].equalsIgnoreCase("on")) {

                player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("commandes.stealOn")));
                Thief.instance.isThief.put(player.getUniqueId(), true);

                return true;

            }

            if(args[0].equalsIgnoreCase("off")){

                player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("commandes.stealOff")));
                Thief.instance.isThief.put(player.getUniqueId(), false);

                return true;
            }

            if(player.hasPermission("thief.admin")){
                if(args[0].equalsIgnoreCase("reload")){


                    //On verifie les joueurs déjà connecté au cas ou c'est un reload
                    for(Player target : Bukkit.getServer().getOnlinePlayers()){

                        config.setup(player);

                    }

                    Thief.instance.getLogger().info("Initialisation des données ...");
                    //On reset toutes les HASHMAPS
                    util.resetAll(player);
                    Thief.instance.getLogger().info("Données READY !");

                    //Sauvegarde de la config et actualisation.
                    Thief.instance.reloadConfig();
                    Thief.instance.saveDefaultConfig();
                    Thief.instance.getLogger().info("Reload de la config : CHECK");

                    //Sauvegarde des messages et actualisation.
                    Messages.save();
                    Messages.reload();
                    Thief.instance.getLogger().info("Reload des messages : CHECK");

                    //Sauvegarde des items et actualisation.
                    Items.save();
                    Items.reload();
                    Thief.instance.getLogger().info("Reload des items : CHECK");


                    Thief.prefix = ChatColor.translateAlternateColorCodes('&', Thief.instance.getConfig().getString("prefix") + " ");

                    player.sendMessage(Thief.prefix + "Le reload est fait !");


                    return true;
                }


            if(args[0].equalsIgnoreCase("list")){



                for(Player thief : Bukkit.getServer().getOnlinePlayers()){


                    if(config.getPlayer(thief).getBoolean("thief".toUpperCase())){

                        voleurs.add(thief.getName());
                    }

                 }


                if(voleurs.isEmpty()){

                    player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("commandes.noThiefs")));
                    return true;

                }

                player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("commandes.liste")));
                for (String str : voleurs){

                    player.sendMessage(Thief.prefix + str);


                }

            return true;

                }
            }



        }




        return false;
    }
}
