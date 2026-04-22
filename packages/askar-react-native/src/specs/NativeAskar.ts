import { type TurboModule, TurboModuleRegistry } from 'react-native'

export interface Spec extends TurboModule {
  /**
   * Installs the JSI-backed Askar bindings on the global object as `_askar`.
   *
   * Under the New Architecture this method is intercepted by the C++
   * `AskarCxxTurboModule` implementation so that it receives the `jsi::Runtime`
   * directly and does not rely on the legacy bridge (works in Bridgeless Mode).
   *
   * Under the Old Architecture the default Objective-C / Java implementation
   * runs, which obtains the runtime from the bridge.
   *
   * @returns `true` on success.
   */
  install(): boolean
}

export default TurboModuleRegistry.getEnforcing<Spec>('Askar')
