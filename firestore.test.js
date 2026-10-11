const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: cannot read user profile", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
});

test("Alice: can create her own user profile with valid schema", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      displayName: "Alice",
      currentStreak: 1,
      longestStreak: 5,
      lastActiveDate: "2026-10-11",
      dailyGoalVerses: 10,
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );
});

test("Bob: cannot read Alice's profile", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      displayName: "Alice",
      currentStreak: 1,
      longestStreak: 5,
      lastActiveDate: "2026-10-11",
      dailyGoalVerses: 10,
      createdAt: new Date(),
      updatedAt: new Date(),
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).get());
});

test("Alice: can create and read a bookmark in her bookmarks subcollection", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bookmarkRef = aliceDb.collection("users").doc(ALICE_UID).collection("bookmarks").doc("bm_1");
  await assertSucceeds(
    bookmarkRef.set({
      userId: ALICE_UID,
      bookId: "gen",
      bookNameSetswana: "Tshimologo",
      bookNameEnglish: "Genesis",
      chapter: 1,
      verse: 1,
      textSetswana: "Mo tshimologong Modimo o ne wa tlhoma legodimo le lefatshe.",
      textEnglish: "In the beginning God created the heavens and the earth.",
      tagColor: "yellow",
      note: "Important creation verse",
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );

  await assertSucceeds(bookmarkRef.get());
});

test("Bob: cannot read Alice's bookmarks", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(ALICE_UID).collection("bookmarks").doc("bm_1").set({
      userId: ALICE_UID,
      bookId: "gen",
      bookNameSetswana: "Tshimologo",
      bookNameEnglish: "Genesis",
      chapter: 1,
      verse: 1,
      textSetswana: "Mo tshimologong Modimo o ne wa tlhoma legodimo le lefatshe.",
      textEnglish: "In the beginning God created the heavens and the earth.",
      tagColor: "yellow",
      createdAt: new Date(),
      updatedAt: new Date(),
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("bookmarks").doc("bm_1").get());
});
