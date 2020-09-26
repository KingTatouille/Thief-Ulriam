package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.PlayersConfig;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent e){

        PlayersConfig getPlayersConfig = new PlayersConfig();
        UtilsRef util = new UtilsRef();


        Thief.instance.isThief.put(e.getPlayer().getUniqueId(), false);
        Thief.instance.stealing.put(e.getPlayer().getUniqueId(), false);

        //Si la personne vient pour la première fois sur le serveur alors on l'enregistre en uuid.yml
        if(!(e.getPlayer().hasPlayedBefore()) || util.checkPlayer(e.getPlayer().getUniqueId()) == false){

            try{

                Thief.instance.getLogger().info("la personne n'est pas dans les fichiers, début de la création ...");
                getPlayersConfig.setup(e.getPlayer());


            } catch(NullPointerException ex){

                ex.printStackTrace();
                Thief.instance.getLogger().severe("Une erreur est survenue !");

            }



        }


    }



}
