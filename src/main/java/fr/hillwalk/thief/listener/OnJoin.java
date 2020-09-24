package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.JoinPlayers;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent e){

        JoinPlayers getJoinPlayers = new JoinPlayers();
        UtilsRef util = new UtilsRef();


        //Si la personne vient pour la première fois sur le serveur alors on l'enregistre en uuid.yml
        if(!(e.getPlayer().hasPlayedBefore()) || util.checkPlayer(e.getPlayer().getUniqueId()) == false){

            try{

                Thief.instance.getLogger().info("la personne n'est pas dans les fichiers, début de la création ...");
                getJoinPlayers.setup(e.getPlayer());


            } catch(NullPointerException ex){

                ex.printStackTrace();
                Thief.instance.getLogger().severe("Une erreur est survenue !");

            }



        }


    }



}
