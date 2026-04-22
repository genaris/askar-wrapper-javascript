require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "askar"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "13.4" }
  s.source       = { :git => "https://github.com/openwallet-foundation/askar-wrapper-javascript", :tag => "#{s.version}" }

  s.header_mappings_dir = "cpp"

  s.pod_target_xcconfig = {
    :USE_HEADERMAP => "No",
    'CLANG_CXX_LANGUAGE_STANDARD' => "c++20",
    'DEFINES_MODULE' => 'YES',
  }

  # TODO(rename): rename to askar when lib name is changed
  s.ios.vendored_frameworks = "native/mobile/ios/aries_askar.xcframework"

  s.source_files = "ios/**/*.{h,m,mm}", "cpp/**/*.{h,cpp}"

  # Use `install_modules_dependencies` (RN >= 0.71) to transparently pull in
  # React-Core / React-Codegen / RCT-Folly / ReactCommon with the right
  # `RCT_NEW_ARCH_ENABLED` define propagated into this pod.
  if respond_to?(:install_modules_dependencies, true)
    install_modules_dependencies(s)
  else
    s.dependency "React-Core"
    s.dependency "React-callinvoker"
    s.dependency "React"
  end
end
