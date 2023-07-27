package angga7togk.autoclearlag;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public final class AutoClearLag extends PluginBase {

    public Config cfg;
    @Override
    public void onEnable() {
        this.saveResource("config.yml");
        this.cfg = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
        this.getServer().getScheduler().scheduleRepeatingTask(new onTask(this), 20);
    }
}
