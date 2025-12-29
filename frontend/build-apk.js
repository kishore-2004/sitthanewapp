const { execSync } = require('child_process');

console.log('Creating EAS project...');
try {
  execSync('eas init --id --non-interactive', { stdio: 'inherit', cwd: __dirname });
} catch (e) {
  console.log('Project may already exist, continuing...');
}

console.log('\nStarting APK build...');
execSync('eas build --platform android --profile preview --non-interactive', { stdio: 'inherit', cwd: __dirname });
