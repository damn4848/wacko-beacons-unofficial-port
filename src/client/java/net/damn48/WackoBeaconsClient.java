package net.damn48;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端入口点：wacko beacons unofficial port。
 *
 * <p>本模组完全由客户端 Mixin 实现，入口点只负责初始化与日志输出，
 * 便于将来扩展（如配置项、开关等）而不必改动 Mixin 结构。</p>
 */
public class WackoBeaconsClient implements ClientModInitializer {

    public static final String MOD_ID = "wacko-beacons-unofficial-port";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("[{}] loaded (client-side beacon exploit PoC)", MOD_ID);
    }
}
