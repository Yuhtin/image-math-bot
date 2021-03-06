package com.yuhtin.imagemath;

import com.yuhtin.imagemath.listeners.ReadImageCommand;
import lombok.val;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class ImageMathBot {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    public static void main(String[] args) {
        try {

            val jdaBuilder = JDABuilder.createDefault(BOT_TOKEN)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(Arrays.asList(GatewayIntent.values()))
                    .addEventListeners(new ReadImageCommand())
                    .build();

            val readImageCommand = new CommandData("readimage", "Resolve math expressions by image link");
            readImageCommand.addOption(OptionType.STRING, "imagelink", "Image link with math expression", true);

            jdaBuilder.updateCommands().addCommands(readImageCommand).queue();

            System.out.println("Online!");

        } catch (LoginException exception) {
            exception.printStackTrace();
            System.exit(0);
        }
    }

}
