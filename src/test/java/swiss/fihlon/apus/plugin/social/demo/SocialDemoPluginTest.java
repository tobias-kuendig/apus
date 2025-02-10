/*
 * Apus - A social wall for conferences with additional features.
 * Copyright (C) Marcus Fihlon and the individual contributors to Apus.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package swiss.fihlon.apus.plugin.social.demo;

import org.junit.jupiter.api.Test;
import swiss.fihlon.apus.configuration.AppConfig;
import swiss.fihlon.apus.configuration.SocialConfig;
import swiss.fihlon.apus.social.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SocialDemoPluginTest {

    @Test
    void getServiceName() {
        final var socialConfig = mock(SocialConfig.class);
        final var configuration = mock(AppConfig.class);
        when(configuration.social()).thenReturn(socialConfig);

        final var demoSocialPlugin = new SocialDemoPlugin(configuration);
        assertEquals("Demo", demoSocialPlugin.getServiceName());
    }

    @Test
    void isEnabled() {
        final var socialConfig = mock(SocialConfig.class);
        when(socialConfig.demoPostCount()).thenReturn(1);
        final var configuration = mock(AppConfig.class);
        when(configuration.social()).thenReturn(socialConfig);

        final var demoSocialPlugin = new SocialDemoPlugin(configuration);
        assertTrue(demoSocialPlugin.isEnabled());
    }

    @Test
    void isDisabled() {
        final var socialConfig = mock(SocialConfig.class);
        when(socialConfig.demoPostCount()).thenReturn(0);
        final var configuration = mock(AppConfig.class);
        when(configuration.social()).thenReturn(socialConfig);

        final var demoSocialPlugin = new SocialDemoPlugin(configuration);
        assertFalse(demoSocialPlugin.isEnabled());
    }

    @Test
    void getPosts() {
        final var socialConfig = mock(SocialConfig.class);
        when(socialConfig.demoPostCount()).thenReturn(1);
        final var configuration = mock(AppConfig.class);
        when(configuration.social()).thenReturn(socialConfig);

        final var socialDemoPlugin = new SocialDemoPlugin(configuration);
        final List<Post> posts = socialDemoPlugin.getPosts().toList();

        assertNotNull(posts);
        assertEquals(1, posts.size());

        final var firstPost = posts.getFirst();
        assertEquals("SOCIAL-DEMO-ID-1", firstPost.id());
        assertTrue(firstPost.author().trim().length() > 5);
        assertTrue(firstPost.avatar().startsWith("https://"));
        assertTrue(firstPost.profile().contains("@"));
        assertEquals(1, firstPost.images().size());
        assertTrue(firstPost.images().getFirst().startsWith("data:image/svg+xml;base64,"));
    }

}
