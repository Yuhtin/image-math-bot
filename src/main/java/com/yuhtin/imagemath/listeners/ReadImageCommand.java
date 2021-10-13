package com.yuhtin.imagemath.listeners;

import com.yuhtin.imagemath.util.ResolveMath;
import lombok.val;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadImageCommand extends ListenerAdapter {

    private static final String LINK_REGEX = "(https?://.*\\.(?:png|jpg))";

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getName().equalsIgnoreCase("readimage")) return;

        val option = event.getOption("imageLink");
        if (option == null) return;

        val imageLink = option.getAsString();
        if (!imageLink.matches(LINK_REGEX)) {
            event.reply(":x: Este comando aceita apenas link de imagens.").queue();
            return;
        }

        val mathExpression = ResolveMath.resolveImageMath(imageLink);
        if (mathExpression == null) {
            event.reply(":x: Link inválido ou inlegível.").queue();
            return;
        }

        val result = ResolveMath.eval(mathExpression);
        event.reply("🎉 O resultado capturado pela imagem é " + result + ".").queue();

    }
}
