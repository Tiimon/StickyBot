import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.discordbots.api.client.DiscordBotListAPI;

import java.awt.*;
import java.text.NumberFormat;

public class JoinNewGuild extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Member stickyBot = event.getGuild().getMemberById(Main.botId);
        Guild stickySupportServer = event.getJDA().getGuildById("641158383138897941");

        EmbedBuilder em = new EmbedBuilder();

        em.setTitle("StickyBOT Joined a new server!");
        em.addField("Server Name: ", event.getGuild().getName(), false);
        em.addField("Server ID", event.getGuild().getId(), false);
        em.addField("Guild Members: ", NumberFormat.getInstance().format(event.getGuild().retrieveMetaData().complete().getApproximateMembers()), false);
        em.addField("Guild Region: ",  event.getGuild().getRegion().getEmoji() + " " + event.getGuild().getRegion().getName(), false);
        em.addField("Guild Owner Tag", event.getGuild().retrieveOwner().complete().getAsMention(), false);
        em.addField("Guild Owner Raw", event.getGuild().retrieveOwner().complete().getEffectiveName() + "#" + event.getGuild().retrieveOwner().complete().getUser().getDiscriminator(), false);
        em.addField("Guild Owner ID", event.getGuild().retrieveOwner().complete().getId(), false);
        em.setFooter("StickyBot is now in " + event.getJDA().getGuilds().size() + " guilds", stickyBot.getUser().getEffectiveAvatarUrl());
        em.setThumbnail(event.getGuild().getIconUrl());
        em.setColor(Color.GREEN);

        stickySupportServer.getTextChannelById("643974985446326272").sendMessage(em.build()).queue();

        //DM server owner info
        event.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue((channel) ->
        {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.yellow);
            eb.setTitle("__Thank you for adding StickyBot to your server!__");
            eb.setDescription("Here are the basics to get you started:");
            eb.addField("Support Server:", "[StickyBot Support](https://discord.gg/SvNQTtf)", false);
            eb.addField("Vote for StickyBot:", "[top.gg/StickyBot](https://top.gg/bot/628400349979344919)", false);
            eb.addField("**Commands:** ", "Do ``?commands`` or ``?help``", false);
            eb.addField("Issues?", "Make sure the bot has perms to send and delete messages and is immune to Slow Mode in the channel.\nJoin the support server with any other issues you may encounter.", false);
            eb.setFooter("StickyBot", Main.jda.getSelfUser().getAvatarUrl());
            channel.sendMessage(eb.build()).queue();
        });

        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token(Main.topggAPIToken)
                .botId(Main.botId)
                .build();

        int serverCount = event.getJDA().getGuilds().size();
        api.setStats(serverCount);
    }
}
