/**
 * Copyright 2019 Systems of Trust BV
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.systemsoftrust.thinkcell;

public class ThinkCellException extends RuntimeException {

    public ThinkCellException() {
    }

    public ThinkCellException(String message) {
        super(message);
    }

    public ThinkCellException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThinkCellException(Throwable cause) {
        super(cause);
    }
}
