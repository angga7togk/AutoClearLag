package angga7togk.autoclearlag;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.entity.item.EntityXPOrb;
import cn.nukkit.entity.mob.EntityMob;
import cn.nukkit.entity.passive.EntityAnimal;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;

import java.util.Objects;

public class onTask extends Task {

    public AutoClearLag plugin;
    public int interval;
    public String prefix;

    public onTask(AutoClearLag plugin){
        this.plugin = plugin;
        this.interval = plugin.cfg.getInt("interval");
        this.prefix = plugin.cfg.getString("prefix")+" §r";
    }

    @Override
    public void onRun(int i) {
        Server server = Server.getInstance();

        this.interval -= 1;

        if(this.interval <= 0){
            int cleared = 0;
            for (Level level : server.getLevels().values()) {
                for (Entity ignored : level.getEntities()){
                    if(ignored instanceof EntityItem){
                        if(this.plugin.cfg.getBoolean("cleared.item")){
                            ignored.despawnFromAll();
                            cleared += 1;
                        }
                    }else if(ignored instanceof EntityAnimal){
                        if(this.plugin.cfg.getBoolean("cleared.animal")){
                            ignored.despawnFromAll();
                            cleared += 1;
                        }
                    }else if(ignored instanceof EntityMob){
                        if(this.plugin.cfg.getBoolean("cleared.mob")){
                            ignored.despawnFromAll();
                            cleared += 1;
                        }
                    }else if (ignored instanceof EntityXPOrb){
                        if(this.plugin.cfg.getBoolean("cleared.xp")){
                            ignored.despawnFromAll();
                            cleared += 1;
                        }
                    }
                }
            }

            this.interval = this.plugin.cfg.getInt("interval");

            for (Player players : server.getOnlinePlayers().values()){
                if(Objects.equals(this.plugin.cfg.getString("type"), "message")){
                    String msg = this.prefix + this.plugin.cfg.getString("message.cleared").replace("{COUNT}", "" + cleared);
                    players.sendMessage(msg);
                }else {
                    String msg = this.plugin.cfg.getString("message.cleared").replace("{COUNT}", "" + cleared);
                    players.sendActionBar(msg);
                }
            }
        }else if(this.plugin.cfg.getIntegerList("times").contains(this.interval)){
            for (Player players : server.getOnlinePlayers().values()){
                if(Objects.equals(this.plugin.cfg.getString("type"), "message")){
                    String msg = this.prefix + this.plugin.cfg.getString("message.running").replace("{TIME}", "" + this.interval);
                    players.sendMessage(msg);
                }else {
                    String msg = this.plugin.cfg.getString("message.running").replace("{TIME}", "" + this.interval);
                    players.sendActionBar(msg);
                }
            }
        }
    }
}
