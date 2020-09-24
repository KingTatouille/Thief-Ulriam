package fr.hillwalk.thief.commands;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.JoinPlayers;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StealCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //On possède la classe util ici
        UtilsRef util = new UtilsRef();
        JoinPlayers config = new JoinPlayers();

        //Si le sender est un joueur
        if(sender instanceof Player){



            //On possède le joueur ici
            Player player = (Player) sender;

            //Si le joueur n'est pas op ou ne possède pas la permission alors il ne peut pas faire les commandes.
            if(!(player.isOp() || player.hasPermission("thief.steal") || player.hasPermission("thief.admin"))){

                player.sendMessage(Thief.prefix + "Vous n'avez pas les droits requis pour faire cette commande !");
                return true;

            }

            //Si le joueur fait /steal sans arguments
            if(args.length == 0){

                player.sendMessage(Thief.prefix + util.getColor("Syntax : /steal on/off"));
                return true;

            }

            if (args[0].equalsIgnoreCase("on")) {

                player.sendMessage(Thief.prefix + "Vous pouvez maintenant voler certains biens !");
                config.getPlayer(player).set("isThief", true);
                config.save(player);

                return true;

            }

            if(args[0].equalsIgnoreCase("off")){

                player.sendMessage(Thief.prefix + "Vous ne pouvez plus rien voler !");
                config.getPlayer(player).set("isThief", false);
                config.save(player);

                return true;
            }


            if(args[0].equalsIgnoreCase("list")){

                player.sendMessage(Thief.prefix + "Voici la liste des voleur(s) :");

                for(Player thief : Bukkit.getServer().getOnlinePlayers()){

                    if(config.getPlayer(thief).getBoolean("isThief")){
                        player.sendMessage(Thief.prefix + config.getPlayer(thief).getString("name"));
                    }

                }



            }



        }




        return false;
    }
}
