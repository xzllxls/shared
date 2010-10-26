/**
 * <p>
 * Copyright (c) 2009 Roy Liu<br>
 * All rights reserved.
 * </p>
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 * </p>
 * <ul>
 * <li>Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.</li>
 * <li>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.</li>
 * <li>Neither the name of the author nor the names of any contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.</li>
 * </ul>
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * </p>
 */

package shared.net.nio;

import shared.event.Handler;
import shared.event.Source;
import shared.net.SourceType;

/**
 * An event {@link Source} that represents its {@link NioConnection} parent.
 * 
 * @author Roy Liu
 */
public class ProxySource<C extends NioConnection<C>> implements Source<NioEvent<?>, SourceType> {

    final C connection;

    /**
     * Default constructor.
     */
    protected ProxySource(C connection) {
        this.connection = connection;
    }

    /**
     * Gets the parent connection.
     */
    protected C getConnection() {
        return this.connection;
    }

    @Override
    public void onLocal(NioEvent<?> evt) {

        // Acquire the connection monitor because the manager thread may change during a handoff.
        synchronized (this.connection.getLock()) {
            this.connection.getThread().onLocal(evt);
        }
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SourceType getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Handler<NioEvent<?>> getHandler() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHandler(Handler<NioEvent<?>> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onRemote(NioEvent<?> evt) {
        throw new UnsupportedOperationException();
    }
}