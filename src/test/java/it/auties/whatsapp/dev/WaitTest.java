package it.auties.whatsapp.dev;

import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.model.info.MessageInfo;
import it.auties.whatsapp.util.JacksonProvider;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class WaitTest implements JacksonProvider {
    @Test
    public void testForFiveMinutes() {
        Whatsapp.lastConnection()
                .addLoggedInListener(() -> System.out.println("Connected"))
                .addNewMessageListener(WaitTest::logMessage)
                .addContactsListener(contacts -> System.out.printf("Contacts: %s%n", contacts.size()))
                .addChatsListener(chats -> System.out.printf("Chats: %s%n", chats.size()))
                .addNodeReceivedListener(incoming -> System.out.printf("Received node %s%n", incoming))
                .addNodeSentListener(outgoing -> System.out.printf("Sent node %s%n", outgoing))
                .addActionListener(action -> System.out.printf("New action: %s%n", action))
                .addSettingListener(setting -> System.out.printf("New setting: %s%n", setting))
                .addAnyMessageStatusListener((chat, contact, info, status) -> System.out.printf("Message %s in chat %s now has status %s for %s %n", info.id(), info.chatName(), status, contact.name()))
                .connect()
                .join()
                .await();
    }

    @SneakyThrows
    private static void logMessage(Whatsapp whatsapp, MessageInfo message) {
        whatsapp.markRead(message);
        System.out.println(JSON.writerWithDefaultPrettyPrinter().writeValueAsString(message));
    }
}
