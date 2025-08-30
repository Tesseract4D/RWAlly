package cn.tesseract.union.api.rusted;

import cn.tesseract.union.api.command.CommandManager;
import com.corrodinggames.rts.union.gameFramework.GameEngine;

public class RustedGame extends RustedWrapper<GameEngine> {
    private CommandManager cmd = new CommandManager();

    public RustedGame(GameEngine instance) {
        super(instance);
    }
}