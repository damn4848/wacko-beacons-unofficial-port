# Wacko Beacons (26.2 Port)

## About This Port

This repository is a **26.2 port** of [getcmdrolled/wacko-beacons](https://github.com/getcmdrolled/wacko-beacons).

- **Original Author**: GetCmdRolled
- **Original Project**: https://github.com/getcmdrolled/wacko-beacons
- **Original License**: CC0 1.0 Universal
- **Changes in This Port**: Adapted the code to Minecraft 26.2 and Fabric Loader 0.19.3, migrated mappings to Mojang official names. No logic changes.

---

## What Is This

This is a proof-of-concept mod demonstrating two server-side validation flaws in Minecraft's beacon effect handling, allowing players to obtain effects they shouldn't be able to get — entirely client-side.

### Flaw 1: Regeneration II

Beacons handle primary and secondary effects separately. The server only checks whether an effect is a *valid beacon effect*, not whether it's a *valid primary effect*, and only requires a 4-layer pyramid when primary and secondary are the same. As a result, Regeneration II can be selected without a full pyramid.

### Flaw 2: High-Tier Effects on a Low-Level Beacon

The server does not validate whether the chosen effect is *reachable* at the current beacon level. Therefore, a level-1 beacon (9 diamond blocks) can grant Strength I (normally requires level 4, 50 diamond blocks).

This mod implements both effects via two client-side Mixins. The server is unaware.

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) 0.19.3 or later
2. Place the mod `.jar` into `.minecraft/mods/`
3. Launch the game

### Requirements

- Minecraft 26.2
- Fabric Loader >= 0.19.3
- Java >= 25
- Fabric API

---

## ⚠️ Disclaimer

This mod is a **client-side exploit**, intended for educational and research purposes only.

- **Do not use on public servers.** It may be detected by anti-cheat systems or violate server rules.
- The user assumes all consequences of using this mod.

---

## License

This port is released under the original project's **CC0 1.0 Universal** license. The original author has waived all copyright, and the modifications in this port are likewise contributed to the public domain.

See [LICENSE](LICENSE).




#chinese
# Wacko Beacons (26.2 移植版)

## 关于本移植

本仓库是 [getcmdrolled/wacko-beacons](https://github.com/getcmdrolled/wacko-beacons) 的 **26.2 移植版**。

- **原作者**：GetCmdRolled
- **原项目**：https://github.com/getcmdrolled/wacko-beacons
- **原项目许可证**：CC0 1.0 Universal
- **本移植版改动**：将代码适配到 Minecraft 26.2 与 Fabric Loader 0.19.3，更新映射至 Mojang 官方命名，其余逻辑未变。

---

## 这是什么

这是一个概念验证模组，演示 Minecraft 服务端在信标（Beacon）效果校验上的两个漏洞，允许玩家在纯客户端环境下获得本不该获得的效果。

### 漏洞一：Regeneration II

信标的主效果和副效果分开处理。服务端只检查"效果是否为合法信标效果"，不检查"它是否可以作为主效果"，并且只在主副效果相同时才要求 4 层金字塔。因此，Regeneration II 可以在不建满金字塔的情况下被选中。

### 漏洞二：低级信标获取高级效果

服务端不校验"当前信标等级是否能获得该效果"。因此，1 级信标（9 个钻石块）即可选择 Strength I（正常需要 4 级、50 个钻石块）。

本模组通过两个客户端 Mixin 实现上述效果，服务端无感知。

---

## 安装

1. 安装 [Fabric Loader](https://fabricmc.net/use/) 0.19.3 或更高版本
2. 将本模组的 `.jar` 文件放入 `.minecraft/mods/`
3. 启动游戏

### 依赖

- Minecraft 26.2
- Fabric Loader >= 0.19.3
- Java >= 25
- Fabric API

---

## ⚠️ 免责声明

本模组为**客户端漏洞利用**，仅供学习与研究使用。

- **请勿在公共服务器使用**，可能被反作弊系统检测或违反服务器规则
- 使用本模组导致的任何后果由使用者自行承担

---

## 许可证

本移植版沿用原项目的 **CC0 1.0 Universal** 许可证。原作者已放弃所有版权，本移植版的修改部分同样贡献至公共领域。

详见 [LICENSE](LICENSE)。