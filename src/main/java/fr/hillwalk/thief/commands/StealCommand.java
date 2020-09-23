package fr.hillwalk.thief.commands;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StealCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        //Si le sender est un joueur
        if(sender instanceof Player){

            //On possède la classe util ici
            UtilsRef util = new UtilsRef();


            //On possède le joueur ici
            Player player = (Player) sender;

            //Si le joueur n'est pas op ou ne possède pas la permission alors il ne peut pas faire les commandes.
            if(!(player.isOp() || player.hasPermission("thief.steal"))){

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

                return true;

            }



        }




        return false;
    }
}
