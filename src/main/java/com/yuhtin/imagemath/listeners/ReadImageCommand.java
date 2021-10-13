package com.yuhtin.imagemath.listeners;

import com.yuhtin.imagemath.util.ResolveMath;
import lombok.val;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadImageCommand extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equalsIgnoreCase("readimage")) return;

        val option = event.getOption("imageLink");
        if (option == null) return;

        val imageLink = option.getAsString();
        val mathExpression = ResolveMath.resolveImageMath(imageLink);
        if (mathExpression == null) {
            event.reply(":x: Link inválido ou inlegivel").queue();
            return;
        }

        val result = ResolveMath.eval(mathExpression);
        event.reply("🎉 O resultado capturado pela imagem é " + result).queue();

    }
}
