const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

console.log('\x1b[36m%s\x1b[0m', '👨‍⚕️  Kiri Store System Doctor');
console.log('\x1b[36m%s\x1b[0m', '==============================');

function check(name, command, failureMsg) {
    process.stdout.write(`Checking ${name}... `);
    try {
        const output = execSync(command, { stdio: 'pipe' }).toString().trim();
        console.log('\x1b[32m%s\x1b[0m', 'PASSED');
        return true;
    } catch (e) {
        console.log('\x1b[31m%s\x1b[0m', 'FAILED');
        if (failureMsg) console.log(`   💡 ${failureMsg}`);
        return false;
    }
}

// 1. Environment Variables
check('Node.js', 'node -v', 'Install Node.js (https://nodejs.org/)');
check('Git', 'git --version', 'Install Git (https://git-scm.com/)');

// 2. JDK (JDK 21 is required for the project)
process.stdout.write(`Checking JDK 21+... `);
try {
    const javaOut = execSync('java -version', { stdio: 'pipe' }).toString() + (execSync('java -version', { stdio: 'pipe' }).stderr?.toString() || '');
    if (javaOut.includes('version "21') || javaOut.includes('version "22') || javaOut.includes('version "23') || javaOut.includes('build 21') || javaOut.includes('build 22')) {
        console.log('\x1b[32m%s\x1b[0m', 'PASSED');
    } else {
        console.log('\x1b[33m%s\x1b[0m', 'WARNING');
        console.log('   ⚠️  JDK 21 is recommended. Your version might cause build issues.');
    }
} catch (e) {
    console.log('\x1b[31m%s\x1b[0m', 'FAILED');
}

// 3. Android SDK
const androidHome = process.env.ANDROID_HOME || process.env.ANDROID_SDK_ROOT;
if (androidHome && fs.existsSync(androidHome)) {
    console.log('\x1b[32m%s\x1b[0m', `✅ Android SDK found at ${androidHome}`);
} else {
    console.log('\x1b[33m%s\x1b[0m', '⚠️  Android SDK not found in environment (ANDROID_HOME).');
    console.log('   Note: Android builds will fail without this.');
}

// 4. local.properties
if (fs.existsSync(path.join(__dirname, 'local.properties'))) {
    const content = fs.readFileSync(path.join(__dirname, 'local.properties'), 'utf8');
    if (content.includes('YOUR_CLIENT_ID_HERE')) {
        console.log('\x1b[33m%s\x1b[0m', '⚠️  local.properties found but GITHUB_CLIENT_ID NOT configured.');
    } else {
        console.log('\x1b[32m%s\x1b[0m', '✅ local.properties configured properly.');
    }
} else {
    console.log('\x1b[31m%s\x1b[0m', '❌ local.properties MISSING. Run "node ks-init.js".');
}

// 5. Gradle Connectivity
check('Gradle Wrapper', 'gradlew -v', 'Ensure gradlew is executable.');

console.log('\n\x1b[36m%s\x1b[0m', 'Diagnostics finished.');

