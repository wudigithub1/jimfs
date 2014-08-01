/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.jimfs;

import static com.google.common.truth.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link FileFactory}.
 *
 * @author Colin Decker
 */
@RunWith(JUnit4.class)
public class FileFactoryTest {

  private FileFactory factory;

  @Before
  public void setUp() {
    factory = new FileFactory(new HeapDisk(2, 2, 0));
  }

  @Test
  public void testCreateFiles_basic() {
    File file = factory.createDirectory();
    ASSERT.that(file.id()).isEqualTo(0L);
    ASSERT.that(file.isDirectory()).isTrue();

    file = factory.createRegularFile();
    ASSERT.that(file.id()).isEqualTo(1L);
    ASSERT.that(file.isRegularFile()).isTrue();

    file = factory.createSymbolicLink(fakePath());
    ASSERT.that(file.id()).isEqualTo(2L);
    ASSERT.that(file.isSymbolicLink()).isTrue();
  }

  @Test
  public void testCreateFiles_withSupplier() {
    File file = factory.directoryCreator().get();
    ASSERT.that(file.id()).isEqualTo(0L);
    ASSERT.that(file.isDirectory()).isTrue();

    file = factory.regularFileCreator().get();
    ASSERT.that(file.id()).isEqualTo(1L);
    ASSERT.that(file.isRegularFile()).isTrue();

    file = factory.symbolicLinkCreator(fakePath()).get();
    ASSERT.that(file.id()).isEqualTo(2L);
    ASSERT.that(file.isSymbolicLink()).isTrue();
  }

  static JimfsPath fakePath() {
    return PathServiceTest.fakeUnixPathService().emptyPath();
  }
}
