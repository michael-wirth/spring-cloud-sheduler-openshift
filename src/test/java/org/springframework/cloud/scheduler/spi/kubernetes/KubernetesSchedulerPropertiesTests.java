/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.scheduler.spi.kubernetes;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link KubernetesSchedulerProperties}.
 *
 * @author Chris Schaefer
 */
@RunWith(Enclosed.class)
public class KubernetesSchedulerPropertiesTests {
	public static class Tests {
		@Test
		public void testImagePullPolicyDefault() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			assertNotNull("Image pull policy should not be null", kubernetesSchedulerProperties.getImagePullPolicy());
			assertEquals("Invalid default image pull policy", ImagePullPolicy.IfNotPresent,
					kubernetesSchedulerProperties.getImagePullPolicy());
		}

		@Test
		public void testImagePullPolicyCanBeCustomized() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			kubernetesSchedulerProperties.setImagePullPolicy(ImagePullPolicy.Never);
			assertNotNull("Image pull policy should not be null", kubernetesSchedulerProperties.getImagePullPolicy());
			assertEquals("Unexpected image pull policy", ImagePullPolicy.Never,
					kubernetesSchedulerProperties.getImagePullPolicy());
		}

		@Test
		public void testRestartPolicyDefault() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			assertNotNull("Restart policy should not be null", kubernetesSchedulerProperties.getRestartPolicy());
			assertEquals("Invalid default restart policy", RestartPolicy.Never,
					kubernetesSchedulerProperties.getRestartPolicy());
		}

		@Test
		public void testRestartPolicyCanBeCustomized() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			kubernetesSchedulerProperties.setRestartPolicy(RestartPolicy.OnFailure);
			assertNotNull("Restart policy should not be null", kubernetesSchedulerProperties.getRestartPolicy());
			assertEquals("Unexpected restart policy", RestartPolicy.OnFailure,
					kubernetesSchedulerProperties.getRestartPolicy());
		}

		@Test
		public void testEntryPointStyleDefault() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			assertNotNull("Entry point style should not be null", kubernetesSchedulerProperties.getEntryPointStyle());
			assertEquals("Invalid default entry point style", EntryPointStyle.exec,
					kubernetesSchedulerProperties.getEntryPointStyle());
		}

		@Test
		public void testEntryPointStyleCanBeCustomized() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			kubernetesSchedulerProperties.setEntryPointStyle(EntryPointStyle.shell);
			assertNotNull("Entry point style should not be null", kubernetesSchedulerProperties.getEntryPointStyle());
			assertEquals("Unexpected entry point stype", EntryPointStyle.shell,
					kubernetesSchedulerProperties.getEntryPointStyle());
		}

		@Test
		public void testNamespaceDefault() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			assertTrue("Namespace should not be empty or null",
					StringUtils.hasText(kubernetesSchedulerProperties.getNamespace()));
			assertEquals("Invalid default namespace", "default", kubernetesSchedulerProperties.getNamespace());
		}

		@Test
		public void testNamespaceCanBeCustomized() {
			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();
			kubernetesSchedulerProperties.setNamespace("myns");
			assertTrue("Namespace should not be empty or null",
					StringUtils.hasText(kubernetesSchedulerProperties.getNamespace()));
			assertEquals("Unexpected namespace", "myns", kubernetesSchedulerProperties.getNamespace());
		}
	}

	@RunWith(PowerMockRunner.class)
	@PrepareForTest({ KubernetesSchedulerProperties.class })
	public static class EnvTests {
		@Test
		public void testNamespaceFromEnvironment() throws Exception {
			PowerMockito.mockStatic(System.class);
			PowerMockito.when(System.getenv(KubernetesSchedulerProperties.ENV_KEY_KUBERNETES_NAMESPACE))
					.thenReturn("nsfromenv");

			KubernetesSchedulerProperties kubernetesSchedulerProperties = new KubernetesSchedulerProperties();

			assertTrue("Namespace should not be empty or null",
					StringUtils.hasText(kubernetesSchedulerProperties.getNamespace()));
			assertEquals("Unexpected namespace from environment", "nsfromenv",
					kubernetesSchedulerProperties.getNamespace());
		}
	}
}